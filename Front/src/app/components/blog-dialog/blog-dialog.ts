import { Component, Inject, Optional } from '@angular/core';
import { DatePipe } from "@angular/common";
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import {
  MatDialogRef,
  MatDialogActions,
  MatDialogClose,
  MatDialogTitle,
  MatDialogContent,
  MAT_DIALOG_DATA
} from '@angular/material/dialog';
import { MatLabel, MatFormField, MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from '@angular/material/input';

// 1
@Component({
  selector: 'dialog-overview',
  imports: [
    FormsModule,
    MatDialogContent,
    MatButtonModule,
    MatFormFieldModule, // ✅ mat-form-field
    MatInputModule // ✅ matInput
],
  providers: [DatePipe],
  templateUrl: 'blog-dialog.html'
})
export class AppDialogBlogOverviewComponent {
  
  local_data: any;
  selectedImage: any = '';
  joiningDate: any = '';
  action: any;

  constructor(
    public datePipe: DatePipe,
    public dialogRef: MatDialogRef<AppDialogBlogOverviewComponent>     
  ) {
       
     
    if (this.local_data !== undefined) {
      this.joiningDate = this.datePipe.transform(
        new Date(this.local_data),
        'yyyy-MM-dd'
      );
    }
    if (this.local_data  === undefined) {
      this.local_data  = 'assets/images/profile/user-1.jpg';
    }
  }

  selectFile(event: any): void {

    if (!event.target.files[0] || event.target.files[0].length === 0) {
      // this.msg = 'You must select an image';
      return;
    }
    const mimeType = event.target.files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      // this.msg = "Only images are supported";
      return;
    }
    // tslint:disable-next-line - Disables all
    const reader = new FileReader();
    reader.readAsDataURL(event.target.files[0]);
    // tslint:disable-next-line - Disables all
    reader.onload = (_event) => {
      // tslint:disable-next-line - Disables all
      this.local_data.imagePath = reader.result;
    };
  }

  doAction(): void {
    this.dialogRef.close({ event: this.action, data: this.local_data });
  }

  closeDialog(): void {
    this.dialogRef.close({ event: 'Cancel' });
  }

} 