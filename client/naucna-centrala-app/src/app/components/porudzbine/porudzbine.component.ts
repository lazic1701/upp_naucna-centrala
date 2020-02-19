import { Component, OnInit } from "@angular/core";
import { PorudzbinaService } from 'src/app/services/porudzbina.service';

@Component({
    selector: "app-porudzbine",
    templateUrl: "./porudzbine.component.html",
    styleUrls: ["./porudzbine.component.css"]
})
export class PorudzbineComponent implements OnInit {

	porudzbine: any[] = null;

    constructor(private porService: PorudzbinaService) {}

    ngOnInit() {
		this.fetchPorudzbine();
	}

	fetchPorudzbine() {
		this.porService.getAllPorudzbine().subscribe(
			(res: any[]) => {
				this.porudzbine = res;
			}, err => console.log(err.error)
		)
	}
}
