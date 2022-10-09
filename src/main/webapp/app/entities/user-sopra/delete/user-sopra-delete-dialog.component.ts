import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserSopra } from '../user-sopra.model';
import { UserSopraService } from '../service/user-sopra.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './user-sopra-delete-dialog.component.html',
})
export class UserSopraDeleteDialogComponent {
  userSopra?: IUserSopra;

  constructor(protected userSopraService: UserSopraService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userSopraService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
