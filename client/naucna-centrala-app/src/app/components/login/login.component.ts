import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

    loginForm = this.fb.group({
		username: ["", Validators.required],
		password: ["", Validators.required]
	});

	feedbackMsg: boolean = false;

	constructor(
		private fb: FormBuilder,
		private userService: UserService,
		private router: Router
	) {}


    ngOnInit() {

    }

    onSubmit() {

		let dto = {
			username: this.loginForm.get("username").value,
			password: this.loginForm.get("password").value
		};

		this.feedbackMsg = false;

		this.userService.login(dto).subscribe(
			payload => payload,
			error => {
				this.feedbackMsg = true;
        }
    );
    }

}
