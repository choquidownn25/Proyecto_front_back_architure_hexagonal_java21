import {Component} from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import {
MatDialogRef,
MatDialogActions,
MatDialogClose,
MatDialogTitle,
MatDialogContent 
} from '@angular/material/dialog';
 

// 1
@Component({
selector: 'dialog-overview',
imports: [MatDialogActions, MatDialogClose, MatDialogTitle, MatDialogContent, MatButtonModule],
templateUrl: 'change-detection-strategy.html'
})
export class AppDialogOverviewComponent {
constructor(public dialogRef: MatDialogRef<AppDialogOverviewComponent>) {}
}
