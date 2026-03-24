import { Component } from '@angular/core';
import { MatCard, MatCardContent } from "@angular/material/card";
import { MatLabel, MatFormField } from "@angular/material/form-field";
import { MatIcon } from "@angular/material/icon";
import { MatOption } from "@angular/material/autocomplete";
import { MatSelect } from "@angular/material/select";
import { MatDatepicker, MatDatepickerModule, MatDatepickerToggle } from "@angular/material/datepicker";
import { MatNativeDateModule } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import { RouterModule } from '@angular/router';
import { AppAddBackendComponent } from "../datatable/backed/add/add.component";

@Component({
  selector: 'app-registro',
  imports: [
    MatCard,
    MatCardContent,
    MatLabel,
    MatFormField,
    MatIcon,
    MatOption,
    MatSelect,
    // 👇 AGREGAR ESTOS
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatDatepickerToggle,
    RouterModule,
    AppAddBackendComponent
],
  templateUrl: './registro.component.html',
  styleUrl: './registro.component.scss'
})
export class RegistroComponent {
hide: any;

}
