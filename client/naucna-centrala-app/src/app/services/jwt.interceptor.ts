import { Injectable } from "@angular/core";
import {
	HttpRequest,
	HttpHandler,
	HttpEvent,
	HttpInterceptor
} from "@angular/common/http";
import { Observable } from "rxjs";
import { JwtHelperService } from "@auth0/angular-jwt";
import { Router } from "@angular/router";
/**
 * Klasa presrece svaki zahtev poslat od korisnika i postavlja u header zahteva token.
 */

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
	public helper = new JwtHelperService();

	constructor(private router: Router) {}

	intercept(
		request: HttpRequest<any>,
		next: HttpHandler
	): Observable<HttpEvent<any>> {
		// add authorization header with jwt token if available
		let currentUser = JSON.parse(localStorage.getItem("currentUser"));

		if (currentUser != null) {
			if (currentUser.accessToken) {
				let isExpired = this.helper.isTokenExpired(
					currentUser.accessToken
				);

				if (isExpired) {
					localStorage.removeItem("currentUser");
					this.router.navigate(["/login"]);
				} else {
					if (currentUser && currentUser.accessToken) {
						if (!request.headers.has("Authorization")) {
							request = request.clone({
								setHeaders: {
									Authorization: `Bearer ${currentUser.accessToken}`
								}
							});
						}
					}
				}
			}
		}
		return next.handle(request);
	}
}
