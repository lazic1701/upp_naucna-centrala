import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

	isLoggedIn: boolean = false;
	role: string = "GUEST";

	constructor(private userService: UserService) {
		this.isLoggedIn = this.userService.isLoggedInSync();
		if (this.isLoggedIn) {
			this.role = this.userService.getRoleFromStorage();
		}
	}

	ngOnInit() {
	}

	onLogout() {
		this.userService.logout();
	}

}
