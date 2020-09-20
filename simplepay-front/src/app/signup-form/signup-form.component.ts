import {Component, OnInit} from '@angular/core';
import {ApplicationUser} from "../shared/applicationuser.model";
import {SecurityService} from "../security.service";
import {FormBuilder, FormGroup} from "@angular/forms";


@Component({
  selector: 'app-signup-form',
  templateUrl: './signup-form.component.html',
  styleUrls: ['./signup-form.component.css']
})
export class SignupFormComponent implements OnInit {
  signUpForm: FormGroup;

  constructor(private securityService: SecurityService,
              private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.initForm();
  }

  signUp() {
    this.securityService.signUp(this.signUpForm.value);
  }

  private initForm(): void {
    this.signUpForm = this.fb.group({
      username: '',
      password: ''
    });
  }
}
