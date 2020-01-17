import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';
@Injectable({
  providedIn: "root"
})
export class UserService {

    private ENDPOINT_URL: string = "api/users";

    constructor(private http: HttpClient, private router: Router) {}

    getRegistrationForm() {
        return this.http.get(this.ENDPOINT_URL + "/registration"); 
    }

    registerUser(taskId: number, dto) {
        return this.http.post(this.ENDPOINT_URL + "/registration/" + taskId, dto);
    }

    verifyUser(processId: string, username: string) {
        return this.http.get(this.ENDPOINT_URL + "/verification/" + processId + "/" + username);
    }

    getLoggedUser() {
        this.http.get(this.ENDPOINT_URL + '/me');
    }

    login(user){
		return this.http.post(this.ENDPOINT_URL + '/login', user).pipe(
			map(user => {
                if(user){
                    localStorage.setItem('currentUser', JSON.stringify(user));
                    window.location.href = "";
            }
				return user;
			})
		)
	}

    logout() {
        this.logoutLocally();
        return this.http.get(this.ENDPOINT_URL + '/logout').subscribe(
            success => {
                this.router.navigate(['']);
                window.location.href = "";
           }, error => {
                this.router.navigate(['']);
                window.location.href = "";
           }
        )
    }

    logoutLocally() {
        localStorage.removeItem('currentUser');
    }

    isLoggedInSync(): boolean {
        let helper = new JwtHelperService();
        let currentUser = JSON.parse(localStorage.getItem('currentUser'));
        if(currentUser != null) {
            if(currentUser.accessToken){
                let isExpired = helper.isTokenExpired(currentUser.accessToken);
                return !isExpired;
            }
        }
        return false;
    }

    putInStorage(key: string, object: Object) {
		if (object) {
            localStorage.setItem(key, JSON.stringify(object));
        }
	}
    
	getIdFromStorage(): number {
        let currentUser = JSON.parse(localStorage.getItem('currentUser'));
        return currentUser.id;
    }
    
    getRoleFromStorage(): string {
        let currentUser = JSON.parse(localStorage.getItem('currentUser'));
        return currentUser.role;
    }

    mapRoleToGroupId(role: string) {
		switch (role) {
			case "ROLE_ADMIN":
				return "administratori";
			case "ROLE_UREDNIK":
				return "urednici";
			case "ROLE_RECENZENT":
				return "recenzenti";
			case "ROLE_AUTOR":
				return "autori";
			default:
				return "";
		}
	}
}
