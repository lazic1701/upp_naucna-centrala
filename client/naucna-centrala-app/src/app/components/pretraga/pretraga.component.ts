import { Component, OnInit } from "@angular/core";

@Component({
    selector: "app-pretraga",
    templateUrl: "./pretraga.component.html",
    styleUrls: ["./pretraga.component.css"]
})
export class PretragaComponent implements OnInit {

    result: any = null
    searchType: string = "";

    constructor() {}

    ngOnInit() {}

    onSimpleSearch() {
        this.searchType = "simple";
    }

    onAdvancedSearch() {
        this.searchType = "advanced";
    }

    onResultsArrived(event) {
        console.log("stigli rezultati")
        this.result = event;
    }
}
