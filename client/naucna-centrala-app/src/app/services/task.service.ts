import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
	


	private ENDPOINT_URL: string = "api/tasks";

	constructor(private http: HttpClient) {}

	getTask(taskId: string) {
		return this.http.get(this.ENDPOINT_URL + "/" + taskId);
	}
	
	getGroupTasks(role: string) {
		return this.http.get(this.ENDPOINT_URL + "/groups/" + this.mapRoleToGroupId(role));
	}

	getUserTasks() {
		return this.http.get(this.ENDPOINT_URL + "/my");
	}

	claimTask(taskId: string) {
		return this.http.post(this.ENDPOINT_URL + "/claim/" + taskId, null);
	}

	claimSetUsernameVariable(taskId: string, variableName: string) {
		return this.http.post(this.ENDPOINT_URL + "/claim/" + taskId + "/" + variableName, null);
	}

	getTaskForm(taskId: string) {
		return this.http.get(this.ENDPOINT_URL + "/form/" + taskId);
	}

	submitForm(dto: any, taskId: string, identifier: string) {
		return this.http.post(this.ENDPOINT_URL + "/form/" + taskId + "/" + identifier, dto);
	}

	mapRoleToGroupId(role: string) {
		switch (role) {
			case "ROLE_ADMIN":
				return "administratori";
			case "ROLE_UREDNIK":
				return "urednici";
			case "ROLE_RECENZENT":
				return "recenzenti";
			case "ROLE_AUTOR":
				return "autori";
			default:
				return "";
		}
	}
}
