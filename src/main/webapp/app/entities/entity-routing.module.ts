import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'user-sopra',
        data: { pageTitle: 'staffingApp.userSopra.home.title' },
        loadChildren: () => import('./user-sopra/user-sopra.module').then(m => m.UserSopraModule),
      },
      {
        path: 'equipe',
        data: { pageTitle: 'staffingApp.equipe.home.title' },
        loadChildren: () => import('./equipe/equipe.module').then(m => m.EquipeModule),
      },
      {
        path: 'demande-conge',
        data: { pageTitle: 'staffingApp.demandeConge.home.title' },
        loadChildren: () => import('./demande-conge/demande-conge.module').then(m => m.DemandeCongeModule),
      },
      {
        path: 'projet',
        data: { pageTitle: 'staffingApp.projet.home.title' },
        loadChildren: () => import('./projet/projet.module').then(m => m.ProjetModule),
      },
      {
        path: 'competance',
        data: { pageTitle: 'staffingApp.competance.home.title' },
        loadChildren: () => import('./competance/competance.module').then(m => m.CompetanceModule),
      },
      {
        path: 'demande',
        data: { pageTitle: 'staffingApp.demande.home.title' },
        loadChildren: () => import('./demande/demande.module').then(m => m.DemandeModule),
      },
      {
        path: 'responsable',
        data: { pageTitle: 'staffingApp.responsable.home.title' },
        loadChildren: () => import('./responsable/responsable.module').then(m => m.ResponsableModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
