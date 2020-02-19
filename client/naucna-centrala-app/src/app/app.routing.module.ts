import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './components/homepage/homepage.component';
import { RegisterComponent } from './components/register/register.component';
import { VerificationComponent } from './components/verification/verification.component';
import { LoginComponent } from './components/login/login.component';
import { TasksComponent } from './components/tasks/tasks.component';
import { TaskListComponent } from './components/tasks/task-list/task-list.component';
import { TaskFormComponent } from './components/tasks/task-form/task-form.component';
import { CasopisiComponent } from './components/casopisi/casopisi.component';
import { CasopisiUrednikComponent } from './components/casopisi-urednik/casopisi-urednik.component';
import { CasopisiListComponent } from './components/casopisi/casopisi-list/casopisi-list.component';
import { CasopisComponent } from './components/casopisi/casopis/casopis.component';
import { PorudzbineComponent } from './components/porudzbine/porudzbine.component';

const appRoutes: Routes = [
    { path: '', redirectTo: '/home', pathMatch: 'full'},
    { path: 'home', component: HomepageComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'login', component: LoginComponent },
    { path: 'verification/:processId/:username', component: VerificationComponent },
    { path: 'tasks', component: TasksComponent, children: [
      {path: '', component: TaskListComponent },
      {path: ':taskId', component: TaskFormComponent},
    ]},
    { path: 'casopisi', component: CasopisiComponent, children: [
      {path: '', component: CasopisiListComponent },
      {path: ':id', component: CasopisComponent }
    ] },
    
    { path: 'casopisi-urednik/:id', component: CasopisiUrednikComponent },
    
    { path: 'porudzbine', component: PorudzbineComponent }
    
    
]


@NgModule({
	imports: [RouterModule.forRoot(appRoutes)],
	exports: [ RouterModule ]
})





export class AppRoutingModule { }