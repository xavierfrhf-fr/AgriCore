import {CommonModule} from '@angular/common';
import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router} from '@angular/router';

import {Auth} from '../../service/auth';

@Component({
  selector: 'app-accueil-page',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './accueil-page.html',
  styleUrl: './accueil-page.css',
})
export class AccueilPage implements OnInit {
  private formBuilder: FormBuilder = inject(FormBuilder);


  loginForm!: any;



  constructor(
      private fb: FormBuilder, private authService: Auth,
      private router: Router) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  connexion() {
    if (this.loginForm.invalid) return;

    const {username, password} = this.loginForm.value;

    this.authService.login(username!, password!).subscribe((res: any) => {
      this.authService.token = res.token;
      console.log('RESPONSE BACKEND:', res);
    });
  }
}
