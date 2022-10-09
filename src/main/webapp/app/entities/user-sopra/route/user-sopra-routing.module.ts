import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UserSopraComponent } from '../list/user-sopra.component';
import { UserSopraDetailComponent } from '../detail/user-sopra-detail.component';
import { UserSopraUpdateComponent } from '../update/user-sopra-update.component';
import { UserSopraRoutingResolveService } from './user-sopra-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const userSopraRoute: Routes = [
  {
    path: '',
    component: UserSopraComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserSopraDetailComponent,
    resolve: {
      userSopra: UserSopraRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserSopraUpdateComponent,
    resolve: {
      userSopra: UserSopraRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserSopraUpdateComponent,
    resolve: {
      userSopra: UserSopraRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(userSopraRoute)],
  exports: [RouterModule],
})
export class UserSopraRoutingModule {}
