import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DemandeCongeComponent } from '../list/demande-conge.component';
import { DemandeCongeDetailComponent } from '../detail/demande-conge-detail.component';
import { DemandeCongeUpdateComponent } from '../update/demande-conge-update.component';
import { DemandeCongeRoutingResolveService } from './demande-conge-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const demandeCongeRoute: Routes = [
  {
    path: '',
    component: DemandeCongeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DemandeCongeDetailComponent,
    resolve: {
      demandeConge: DemandeCongeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DemandeCongeUpdateComponent,
    resolve: {
      demandeConge: DemandeCongeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DemandeCongeUpdateComponent,
    resolve: {
      demandeConge: DemandeCongeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(demandeCongeRoute)],
  exports: [RouterModule],
})
export class DemandeCongeRoutingModule {}
