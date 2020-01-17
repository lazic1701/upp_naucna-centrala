import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

    private formFieldsDTO = null;
    private verificationMessage: boolean = false;

    enumValues: string[];

    constructor(private userService: UserService) { }

    ngOnInit() {
        this.userService.getRegistrationForm().subscribe(
            (res:any) => {
                this.formFieldsDTO = res;
                this.formFieldsDTO.formFields.forEach(
                    field => {
                        if( field.type.name == 'enum'){
                            this.enumValues = Object.keys(field.type.values);
                        }
                });
            }, err => console.log(err.error)
        )
    }

    onSubmit(value, form){ 

        let fsDTO = [];

        Object.keys(value).forEach(prop => {
            if (prop == "naucneOblasti") {
                fsDTO.push(...value[prop].map(no => { return {fieldId: prop, fieldValue: no} }));
            } else {
                fsDTO.push({fieldId: prop, fieldValue: value[prop]});
            }
        });

        this.userService.registerUser(this.formFieldsDTO.taskId, fsDTO).subscribe(
            res => {
                this.verificationMessage = true;
            }, err => console.log(err.error)
        )
    }

}
