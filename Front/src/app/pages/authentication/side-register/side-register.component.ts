import { RegisterRequest } from './../../../models/register';
import { Component } from '@angular/core';
import { CoreService } from 'src/app/services/core.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MaterialModule } from 'src/app/material.module';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-side-register',
  imports: [RouterModule, MaterialModule, FormsModule, ReactiveFormsModule],
  templateUrl: './side-register.component.html',
})
export class AppSideRegisterComponent {
  options = this.settings.getOptions();

  constructor(private settings: CoreService, private router: Router) {}

  form = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.minLength(6)]),
    email: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
    rol: new FormControl('', [Validators.required])
  });

  get f() {
    return this.form.controls;
  }

  submit() {
    console.log(this.form.value);
    if(this.form.value.username ==='' && this.form.value.password==='' && this.form.value.email==='' ){
      return
    }
    const usuario: RegisterRequest = {
      username: this.form.value.username!,
      email: this.form.value.email!,
      password: this.form.value.password!,
      rol: this.form.value.rol!
    };
    this.settings.register(usuario).subscribe({
      next: (res) => {
        console.log('Registration successful', res);
        this.router.navigate(['/']);
      },
      error: (err) => {
        console.error('Registration failed', err);

        Swal.fire({
                  icon: 'error',
                  title: 'Oops...',
                  text: 'Something went wrong!' + "\n" + err.message.toString,
                  footer: '<a href="">Why do I have this issue?</a>'
                })

      }
    });
  }
}
