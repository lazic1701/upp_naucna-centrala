import { Component, OnInit } from "@angular/core";
import { CasopisService } from "src/app/services/casopis.service";
import { ActivatedRoute, Params } from "@angular/router";
import { KpServiceService } from 'src/app/services/kp-service.service';

@Component({
    selector: "app-casopisi-urednik",
    templateUrl: "./casopisi-urednik.component.html",
    styleUrls: ["./casopisi-urednik.component.css"]
})
export class CasopisiUrednikComponent implements OnInit {
    casopisi: any[] = null;
    glavniUrednikId: number;

    constructor(
		private casopisService: CasopisService,
		private kpService: KpServiceService,
        private route: ActivatedRoute
    ) {
        this.route.params.subscribe((params: Params) => {
            this.glavniUrednikId = +params["id"];
            if (!isNaN(this.glavniUrednikId)) {
                this.fetchGlavniUrednikCasopisi();
            }
        });
    }

    ngOnInit() {}

    fetchGlavniUrednikCasopisi() {
        this.casopisService
            .getAllGlavniUrednikCasopisi(this.glavniUrednikId)
            .subscribe(
                (res: any[]) => {
                    this.casopisi = res;
                },
                err => console.log(err.error)
            );
    }

    onRegistration(casopis) {
		let dto = {
			id: casopis.id
		  }
	  
		  this.kpService.registerMagazinSeller(dto).subscribe(
			(res: any) => {
			  this.casopisi = this.casopisi.map(c => {
				if (c.id == dto.id) {
				  c.sellerId = "x";
				}
	  
				return c;
			  })
			  window.location.href = res.registrationPageRedirectUrl;
			}, err=> console.log(err.error)
		  )
	}

    onReviewRegistration(casopis) {
		let dto = {
			id: casopis.id
		  }
	  
		  this.kpService.reviewRegistration(dto).subscribe(
			(res: any) => {
			  window.location.href = res.registrationPageRedirectUrl;
			}, err => console.log(err.error)
		  )
	}
}
