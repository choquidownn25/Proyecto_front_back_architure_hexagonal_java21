import { DatePipe } from "@angular/common";
import { Component, Inject, Optional } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from "@angular/material/dialog";
import { MaterialModule } from "src/app/material.module";
import { Producto } from "src/app/models/producto";

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'app-dialog-content',
    imports: [MatDialogModule, FormsModule, MaterialModule],
    providers: [DatePipe],
    templateUrl: 'backend-product-dialog-content.html'
})
export class AppBackendDialogProductContent {
  action: string;
  // tslint:disable-next-line - Disables all
  local_data: any;
  selectedImage: any = '';
  constructor(
    public dialogRef: MatDialogRef<AppBackendDialogProductContent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: Producto
  ) {
      this.local_data = { ...data };
    this.action = this.local_data.action;
  }
  doAction() : void {
    this.dialogRef.close({ event: this.action, data: this.local_data });
  }
  closeDialog(): void {
    this.dialogRef.close({ event: 'Cancel' });
  }
  selectFile(event: any) : void {

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
      this.local_data.imagen = reader.result;
    };
  }
}
