import { Component, OnInit } from '@angular/core';
import { TaskService } from 'src/app/services/task.service';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { CasopisService } from 'src/app/services/casopis.service';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {

	groupTasks: any[] = null;
	userTasks: any[] = null;
	role: string = "";
	groupId: string = "";
	

	constructor(private taskService: TaskService, private userService: UserService, private casopisService: CasopisService, private router: Router) { 
		this.role = this.userService.getRoleFromStorage();
		this.groupId = this.userService.mapRoleToGroupId(this.role);
	}

	ngOnInit() {
		this.fetchUserTasks();
		this.fetchGroupTasks();
	}

	fetchGroupTasks() {
		this.taskService.getGroupTasks(this.userService.getRoleFromStorage()).subscribe(
			(res: any[]) => {
				this.groupTasks = res;
			}, err => console.log(err.error)
		)
	}

	fetchUserTasks() {
		this.taskService.getUserTasks().subscribe(
			(res: any[]) => {
				this.userTasks = res;
			}, err => console.log(err.error)
		)
	}

	onClaim(taskId: string) {
		this.taskService.claimSetUsernameVariable(taskId, "assignedUser_" + this.userService.getRoleFromStorage()).subscribe(
			res => {
				this.groupTasks = this.groupTasks.filter(gt => gt.taskId !== taskId);
				this.fetchUserTasks();
			}, err => console.log(err.error)
		)
	}

	onExecute(taskId: string) {
		this.router.navigate(['/tasks/' + taskId]);
	}

	onKreiranjeCasopisa() {
		this.casopisService.initKreiranjeCasopisa().subscribe(
			res => {
				this.fetchGroupTasks();
			}, err => console.log(err.error)
		)
	}

}
