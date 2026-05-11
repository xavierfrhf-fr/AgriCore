import { CommonModule } from '@angular/common';
import { Component, EventEmitter, inject, OnInit, Output } from '@angular/core';
import { FormBuilder,FormsModule,ReactiveFormsModule,Validators } from '@angular/forms';
import { Auth } from '../../service/auth';
import { Router } from '@angular/router';
import { Message } from '../../model/message';
import { MessageModal } from '../../component/message-modal/message-modal';

@Component({
  selector: 'app-accueil-page',
  imports: [ReactiveFormsModule,CommonModule, MessageModal],
  templateUrl: './accueil-page.html',
  styleUrl: './accueil-page.css',
})
export class AccueilPage implements OnInit {

  @Output() messageEvent: EventEmitter<Message> = new EventEmitter<Message>();

  private formBuilder: FormBuilder = inject(FormBuilder);
  protected afficherInscription: boolean = false;

  loginForm!: any;
  registerForm!: any;

  protected message?: Message;
  

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
      email: ['', [Validators.required,Validators.email] ]
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
        } else if (this.authService.isClient()) {
          this.router.navigate(['/boutique']);
        } else {
          this.router.navigate(['/animal'])
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
        this.showMessage({ message:"Inscription réussie",
                           type: "success",
                           timeout: 3000
        });
        this.closeInscription();
        this.registerForm.reset();
        this.router.navigate(['/accueil']);
      },
      (error: any) => {
        this.showMessage({
        message: "Erreur lors de l'inscription ou utilisateur déjà inscrit",
        type: "error",
        timeout: 5000
      });
        this.closeInscription();
        this.registerForm.reset();
        this.router.navigate(['/accueil']);

        console.log(error)
      }
    );
  }

  private showMessage_old(text: string, type: 'message' | 'error') {
      const msg = {} as Message;
      msg.message = text;
      msg.type = type;
      msg.position = {
        top:'35%',
        left: '80%'
      };
      msg.timeout= 5000;
  
      this.messageEvent.emit(msg)
  
    }
  
  showMessage($event: Message): void {
    this.message = $event;
  }

  clearMessage(): void {
    this.message = undefined;
  }

}
