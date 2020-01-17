import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { ActivatedRoute, Params } from '@angular/router';

@Component({
  selector: 'app-verification',
  templateUrl: './verification.component.html',
  styleUrls: ['./verification.component.css']
})
export class VerificationComponent implements OnInit {

	processId: string;
	username: string;

	verified: boolean = false;

	constructor(private userService: UserService, private route: ActivatedRoute) {
		this.route.params.subscribe(
			(params: Params) => {
			  this.processId = params['processId'];
			  this.username = params['username'];

			  this.verifyUser();
			}
		  );
	 }

	ngOnInit() {
	}

	verifyUser() {
		this.userService.verifyUser(this.processId, this.username).subscribe(
			(res: any) => {
				this.verified = true;
			}, err => console.log(err.error)
		)
	}

}
