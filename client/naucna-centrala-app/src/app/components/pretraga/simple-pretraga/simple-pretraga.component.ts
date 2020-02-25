import { Component, OnInit, Output, EventEmitter } from "@angular/core";
import { FormBuilder, Validators } from '@angular/forms';
import { PretragaService } from 'src/app/services/pretraga.service';

@Component({
    selector: "app-simple-pretraga",
    templateUrl: "./simple-pretraga.component.html",
    styleUrls: ["./simple-pretraga.component.css"]
})
export class SimplePretragaComponent implements OnInit {
	
	@Output() output: EventEmitter<any> = new EventEmitter();

	pretragaForm = this.fb.group({
        fieldValue: ["", Validators.required],
		fieldName: ["", [Validators.required]]
	})
	
    constructor(private fb: FormBuilder, private pretragaService: PretragaService) {}

	ngOnInit() {}
	
	onSubmit() {
		console.log(this.pretragaForm);

		const dto = {
			fieldName: this.pretragaForm.get("fieldName").value,
			fieldValue: this.pretragaForm.get("fieldValue").value
		}

		console.log(dto)

		this.pretragaService.simpleSearch(dto).subscribe(
			res => this.output.emit(res),
			err => console.log(err)
		)
	}
}
