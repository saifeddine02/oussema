import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UserSopraComponent } from './list/user-sopra.component';
import { UserSopraDetailComponent } from './detail/user-sopra-detail.component';
import { UserSopraUpdateComponent } from './update/user-sopra-update.component';
import { UserSopraDeleteDialogComponent } from './delete/user-sopra-delete-dialog.component';
import { UserSopraRoutingModule } from './route/user-sopra-routing.module';

@NgModule({
  imports: [SharedModule, UserSopraRoutingModule],
  declarations: [UserSopraComponent, UserSopraDetailComponent, UserSopraUpdateComponent, UserSopraDeleteDialogComponent],
})
export class UserSopraModule {}
