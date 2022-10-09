import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDemandeConge } from '../demande-conge.model';
import { DemandeCongeService } from '../service/demande-conge.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './demande-conge-delete-dialog.component.html',
})
export class DemandeCongeDeleteDialogComponent {
  demandeConge?: IDemandeConge;

  constructor(protected demandeCongeService: DemandeCongeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.demandeCongeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
