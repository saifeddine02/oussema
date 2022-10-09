import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserSopra } from '../user-sopra.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-user-sopra-detail',
  templateUrl: './user-sopra-detail.component.html',
})
export class UserSopraDetailComponent implements OnInit {
  userSopra: IUserSopra | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userSopra }) => {
      this.userSopra = userSopra;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
