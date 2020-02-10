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

	uploadPDF(file, pid) {
		return this.http.post(this.ENDPOINT_URL + "/file/" + pid, file);
	}

	downloadPDF(pid) {
		const httpOptions = {
			'responseType'  : 'arraybuffer' as 'json'
		  };
		return this.http.get(this.ENDPOINT_URL + "/file/" + pid, httpOptions);
	}


}
