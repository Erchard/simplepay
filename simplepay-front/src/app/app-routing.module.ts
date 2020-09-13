import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {CabinetComponent} from "./cabinet/cabinet.component";
import {SignupFormComponent} from "./signup-form/signup-form.component";
import {LoginFormComponent} from "./login-form/login-form.component";
import {RouterModule, Routes} from "@angular/router";

const routes: Routes = [
  {path: 'appuser/signup', component: SignupFormComponent},
  {path: 'appuser/login', component: LoginFormComponent},
  {path: 'appuser/cabinet', component: CabinetComponent}
];

@NgModule({
  imports: [
   RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
