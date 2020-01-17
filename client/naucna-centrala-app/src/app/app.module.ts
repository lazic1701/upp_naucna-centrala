import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { RegisterComponent } from './components/register/register.component';
import { AppRoutingModule } from './app.routing.module';
import { HomepageComponent } from './components/homepage/homepage.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import {  HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http'
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { JwtModule } from "@auth0/angular-jwt";
import { JwtInterceptor } from "./services/jwt.interceptor";
import { VerificationComponent } from './components/verification/verification.component';
import { LoginComponent } from './components/login/login.component';
import { TasksComponent } from './components/tasks/tasks.component';
import { TaskFormComponent } from './components/tasks/task-form/task-form.component';
import { TaskListComponent } from './components/tasks/task-list/task-list.component';
import { CasopisiComponent } from './components/casopisi/casopisi.component'

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    HomepageComponent,
    NavbarComponent,
    VerificationComponent,
    LoginComponent,
    TasksComponent,
    TaskFormComponent,
    TaskListComponent,
    CasopisiComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    JwtModule.forRoot({
			config: {
				tokenGetter: tokenGetter,
				whitelistedDomains: [],
				blacklistedRoutes: []
			}
		})
  ],
  providers: [ { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }

export function tokenGetter() {
	return localStorage.getItem("access_token");
}
