import {Component, OnInit} from '@angular/core';
import {SecurityService} from "../security.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  loginForm: FormGroup;

  constructor(private securityService: SecurityService,
              private fb: FormBuilder,
              private router: Router) {
  }

  ngOnInit(): void {
    this.initForm();
  }

  logIn() {
    this.securityService.logIn(this.loginForm.value).subscribe(
      () => {
        this.router.navigate(['appuser/cabinet']);
      }
    );
  }

  private initForm(): void {
    this.loginForm = this.fb.group({
      username: '',
      password: ''
    });
  }
}
