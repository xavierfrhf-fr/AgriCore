import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder,FormsModule,ReactiveFormsModule,Validators } from '@angular/forms';
import { Auth } from '../../service/auth';
import { Router } from '@angular/router';

@Component({
  selector: 'app-accueil-page',
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './accueil-page.html',
  styleUrl: './accueil-page.css',
})
export class AccueilPage implements OnInit {

  private formBuilder: FormBuilder = inject(FormBuilder);
  protected afficherInscription: boolean = false;

  loginForm!: any;
  registerForm!: any;
  

  constructor(private fb: FormBuilder,private authService: Auth, private router: Router) {}
  
  ngOnInit(): void {
  this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
  });

  this.registerForm = this.fb.group({
      login: ['', Validators.required],
      password: ['', Validators.required],
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      email: ['', Validators.required]
    })

  }

  connexion() {
    if(this.loginForm.invalid) return;

    const { username, password } = this.loginForm.value;

    this.authService.login(username!,password!).subscribe({
      next: (authResponse: any) => {
        this.authService.saveAuthData(authResponse);
        console.log("Connexion réussie:", authResponse);
        console.log(this.authService.getRole());
        console.log(this.authService.getUsername())

        // Redirection basée sur le rôle
        if (this.authService.isFermier()) {
          this.router.navigate(['/gestion-employes']);
        } else {
          this.router.navigate(['/animal']);
        }
      },
      error: (error: any) => {
        console.error("Erreur de connexion:", error);
        // Ici vous pouvez ajouter une gestion d'erreur (message d'erreur à l'utilisateur)
      }
    });
  }

  openInscription() {
    this.afficherInscription = true;
    
    
  }

  closeInscription() {
    this.afficherInscription = false;
  }



  Inscription() {
    if(this.registerForm.invalid) {
      console.error("Formulaire invalide");
      return;
    }
    
    const {login,password,nom,prenom,email }= this.registerForm.value;

    this.authService.register(login!,password!,nom!,prenom!,email!).subscribe( 
      (res: any) => {
        console.log("Inscription réussie", res);
        this.closeInscription();
        this.registerForm.reset();
      },
      (error: any) => {
        console.error("Erreur lors de l'inscription:", error);
      }
    );
  }

}
