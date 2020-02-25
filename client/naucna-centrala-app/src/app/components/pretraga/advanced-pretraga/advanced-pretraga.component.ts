import { Component, OnInit, EventEmitter, Output } from "@angular/core";
import { FormBuilder, Validators } from '@angular/forms';
import { PretragaService } from 'src/app/services/pretraga.service';

@Component({
    selector: "app-advanced-pretraga",
    templateUrl: "./advanced-pretraga.component.html",
    styleUrls: ["./advanced-pretraga.component.css"]
})
export class AdvancedPretragaComponent implements OnInit {

	@Output() output: EventEmitter<any> = new EventEmitter();

	pretragaForm = this.fb.group({
        operator: ["", Validators.required],
		casopisNaziv: ["", [Validators.required]],
		naslov: ["", [Validators.required]],
		autor: ["", [Validators.required]],
		kljucniPojmovi: ["", [Validators.required]],
		tekst: ["", [Validators.required]],
		naucneOblasti: ["", [Validators.required]]
	})

	
    constructor(private fb: FormBuilder, private pretragaService: PretragaService) {}

	ngOnInit() {}
	
	onSubmit() {
		let queries = []
		let operator = "OR"
		
		for (const value in this.pretragaForm.value) {
			if (value === "operator") {
				operator = this.pretragaForm.value[value]
			} else if (value === "casopisNaziv") {
				if (this.pretragaForm.value[value])
					queries.push({
						fieldName: "casopis.naziv",
						fieldValue : this.pretragaForm.value[value]})
			} else if (value === "autor") {
				if (this.pretragaForm.value[value])
					queries.push({
						fieldName: "autori.ime",
						fieldValue : this.pretragaForm.value[value]})
			} else if (value === "naucneOblasti") {
				if (this.pretragaForm.value[value])
					queries.push({
						fieldName: "autori.naucneOblasti",
						fieldValue : this.pretragaForm.value[value]})
			} else {
				if (this.pretragaForm.value[value])
					queries.push({
						fieldName: value,
						fieldValue : this.pretragaForm.value[value]})
			}
		}

		let dto = {
			operator: operator,
			queries: queries
		}

		console.log(dto)

		this.pretragaService.advancedSerach(dto).subscribe(
			res => this.output.emit(res),
			err => console.log(err)
		)
	}
}
