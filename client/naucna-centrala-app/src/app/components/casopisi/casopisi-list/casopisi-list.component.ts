import { Component, OnInit } from "@angular/core";
import { CasopisService } from 'src/app/services/casopis.service';
import { PorudzbinaService } from 'src/app/services/porudzbina.service';

@Component({
    selector: "app-casopisi-list",
    templateUrl: "./casopisi-list.component.html",
    styleUrls: ["./casopisi-list.component.css"]
})
export class CasopisiListComponent implements OnInit {
	
	casopisi: any[] = [];

	constructor(private casService: CasopisService, private porService: PorudzbinaService) {}
	
	ngOnInit() {
		this.fetchCasopisi();
	}
	
	fetchCasopisi() {
		this.casService.getAllCasopisi().subscribe(
			(res: any[]) => {
				this.casopisi = res;
			}
		)
	}

	onKupiCasopis(c) {
		const dto = {
			id: c.id
		}

		this.porService.kupiCasopis(c).subscribe(
			(res: any) => {
				window.location.href = res.redirectUrl;
			}, err => console.log(err)
		)
	}

	onKupiClanarinu(c) {
		const dto = {
			casopis: {
				id: c.id
			},
			paymentType: "NAPLACIVANJE_CITAOCU"
		}

		this.porService.kupiClanarinu(dto).subscribe(
			(res: any) => {
				window.location.href = res.redirectUrl;
			}, err => console.log(err)
		)
	}
}
