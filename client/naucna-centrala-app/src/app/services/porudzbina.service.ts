import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: "root"
})
export class PorudzbinaService {

	private ENDPOINT_URL: string = "api/porudzbine";

	constructor(private http: HttpClient) {}

	public getAllPorudzbine() {
		return this.http.get(this.ENDPOINT_URL + "/");
	}

	public kupiCasopis(dto) {
		return this.http.post(this.ENDPOINT_URL + "/kupovina/casopis", dto);
	}

	public kupiNaucniRad(dto) {
		return this.http.post(this.ENDPOINT_URL + "/kupovina/naucni-rad", dto);
	}

	public kupiClanarinu(dto) {
		return this.http.post(this.ENDPOINT_URL + "/kupovina/clanarina", dto);
	}


}
