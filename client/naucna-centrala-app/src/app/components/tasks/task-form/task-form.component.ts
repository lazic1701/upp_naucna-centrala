import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { TaskService } from 'src/app/services/task.service';
import { NaucniRadService } from 'src/app/services/naucni-rad.service';

@Component({
  selector: 'app-task-form',
  templateUrl: './task-form.component.html',
  styleUrls: ['./task-form.component.css']
})
export class TaskFormComponent implements OnInit {

  taskId: string = "";
  formFieldsDTO: any = null;
  task: any = null;

  uploadPDF: File = null;
  uploadPDFPath: string = "";

  successMessage: boolean = false;
  errorMessage = null;

  constructor(private route: ActivatedRoute, private taskService: TaskService, private nrService: NaucniRadService) { }

  ngOnInit() {
	this.route.params.subscribe(
			(params: Params) => {
			  this.taskId = params['taskId'];
			  this.getTaskForm();
			  this.getTask();
			}
		  );
  }

  getTask() {
	this.taskService.getTask(this.taskId).subscribe(
		(res: any) => {
			this.task = res;
		}, err => console.log(err.error)
	)
  }

  getTaskForm() {
	  this.taskService.getTaskForm(this.taskId).subscribe(
		  (res: any) => {
			    this.formFieldsDTO = res;
 				this.formFieldsDTO.formFields.forEach(
                    field => {
                        if( field.type.name == 'enum'){
                            field.enumValues = Object.keys(field.type.values);
                        }
                });
		  }, err => console.log(err.error)
	  )
	  
  }

  	onSubmit(value, form){ 

		this.errorMessage = null;
	
		let fsDTO = [];

		// TODO: handling enums
		Object.keys(value).forEach(prop => {
			if (prop.includes("_multiselect")) {
				fsDTO.push(...value[prop].map(ms => { return {fieldId: prop, fieldValue: ms} }));
			} else {
				fsDTO.push({fieldId: prop, fieldValue: value[prop]});
			}
		});

		if (this.task.name === "Izbor recenzenata") {
		
			const validationResult = this.validateDto(fsDTO);
			if (!validationResult[0]) {
				this.errorMessage = validationResult[1];
				return;
			}
		}

		
			
		

		this.taskService.submitForm(fsDTO, this.formFieldsDTO.taskId, "submittedForm").subscribe(
			res => {
				if (this.task.name === "Unos informacija o NR" || this.task.name === "Ispravka sadržaja rada") {
					this.uploadFile();
					return;
				}
				this.successMessage = true;
			}, err => console.log(err.error)
		)
	}


	uploadFile() {

		let formData = new FormData();
		formData.append('file', this.uploadPDF);

		this.nrService.uploadPDF(formData, this.formFieldsDTO.processInstanceId).subscribe(
			res => {
				this.successMessage = true;
			}, err => console.log(err.error)
		)

		
	}

	validateDto(dto) {


		let recCnt = 0;
		dto.forEach(x => {
			if (x.fieldId.includes('f_recenzenti')) {
				recCnt++;
			}
		})

		if (recCnt < 2) {
			return [false, "Morate izabrati najmanje 2 recenzenta."];
		} else {
			return [true, null];
		}
	}

	handleUploadPDF(files) {
		this.uploadPDF = files.item(0);
		var reader = new FileReader();
		reader.onload = (event:any) => {
		  this.uploadPDFPath = event.target.result;
		}
		reader.readAsDataURL(this.uploadPDF);
		console.log("URL "+this.uploadPDFPath);
		console.log("file "+this.uploadPDF.name);
	}

	onPregledajte() {
		this.nrService.downloadPDF(this.formFieldsDTO.processInstanceId).subscribe(
			(res:any) => {
				let blob = new Blob([res], {type: 'application/pdf'});
				let url = window.URL.createObjectURL(blob);
				window.open(url, "_blank");
			}, err => console.log(err)
		)
	}




}
