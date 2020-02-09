import { Component, OnInit } from "@angular/core";
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { NaucniRadService } from 'src/app/services/naucni-rad.service';

@Component({
    selector: "app-homepage",
    templateUrl: "./homepage.component.html",
    styleUrls: ["./homepage.component.css"]
})
export class HomepageComponent implements OnInit {

	role: string = '';
    constructor(private userSer: UserService, private nrSer: NaucniRadService, private router: Router) {
		this.role = this.userSer.getRoleFromStorage();
	}

	ngOnInit() {}
	
	onObjaviteNR() {
		if (this.userSer.isLoggedInSync()) {
			this.initObjavaNR();
		} else {
			this.router.navigate(['/login']);
		}
	}

	initObjavaNR() {
		this.nrSer.initKreiranjeCasopisa().subscribe(
			res => {
				this.router.navigate(['/tasks']);
			}, err => {
				alert(err.error.message);
			}
		)
	}
}
