import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CasopisService {

	
	private ENDPOINT_URL: string = "api/casopisi";

	constructor(private http: HttpClient) {}

	initKreiranjeCasopisa() {
		return this.http.get(this.ENDPOINT_URL + "/init");
	}

}