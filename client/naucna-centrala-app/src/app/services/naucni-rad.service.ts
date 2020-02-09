import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

@Injectable({
    providedIn: "root"
})
export class NaucniRadService {

	private ENDPOINT_URL: string = "api/naucni-radovi";

    constructor(private http: HttpClient) {

	}

	initKreiranjeCasopisa() {
		return this.http.get(this.ENDPOINT_URL + "/init");
	}


}
