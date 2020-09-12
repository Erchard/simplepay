import {Component, OnInit} from '@angular/core';
import {ApplicationUser} from "../shared/applicationuser.model";
import {SecurityService} from "../security.service";

@Component({
  selector: 'app-signup-form',
  templateUrl: './signup-form.component.html',
  styleUrls: ['./signup-form.component.css']
})
export class SignupFormComponent implements OnInit {

  applicationuser: ApplicationUser = {username: '', password: ''};

  constructor(private securityService: SecurityService) {
  }

  ngOnInit(): void {
  }

  signUp() {
    this.securityService.signUp(this.applicationuser);
  }
}
