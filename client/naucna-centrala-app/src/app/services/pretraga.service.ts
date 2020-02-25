import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';

@Injectable({
    providedIn: "root"
})
export class PretragaService {
    private ENDPOINT_URL: string = "api/search";

	constructor(private http: HttpClient) {}

	simpleSearch(dto) {
		return this.http.post(this.ENDPOINT_URL + "/simple/", dto);
	}

	advancedSerach(dto) {
		return this.http.post(this.ENDPOINT_URL + "/bool/", dto);
	}
}
