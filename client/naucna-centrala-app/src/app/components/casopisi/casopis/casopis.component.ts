import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Params } from '@angular/router';

@Component({
    selector: "app-casopis",
    templateUrl: "./casopis.component.html",
    styleUrls: ["./casopis.component.css"]
})
export class CasopisComponent implements OnInit {

	casopisId: number;

    constructor(private route: ActivatedRoute) {
		this.route.params.subscribe((params: Params) => {
            this.casopisId = +params["id"];
            if (!isNaN(this.casopisId)) {
                this.fetchCasopis();
            }
        });
	}

	ngOnInit() {}
	
	fetchCasopis() {
		
	}
}
