import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: "root"
})
export class KpServiceService {

	
	ENDPOINT_URI_CASOPISI = "api/casopisi";

	constructor(private http: HttpClient) {}
	
	registerMagazinSeller(casopis) {
		return this.http.post(this.ENDPOINT_URI_CASOPISI + '/registration', casopis);
	}
	
	reviewRegistration(casopis) {
		return this.http.post(this.ENDPOINT_URI_CASOPISI + '/registration/review', casopis);
	}
}
