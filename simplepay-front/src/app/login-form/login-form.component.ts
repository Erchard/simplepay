import { Component, OnInit } from '@angular/core';
import {ApplicationUser} from "../shared/applicationuser.model";
import {SecurityService} from "../security.service";

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  applicationuser: ApplicationUser = {username: '', password: ''};

  constructor(private securityService: SecurityService) {
  }

  ngOnInit(): void {
  }

  logIn() {
    this.securityService.logIn(this.applicationuser);
  }
}
