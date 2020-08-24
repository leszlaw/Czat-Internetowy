import { NgModule } from '@angular/core';
import { Routes, RouterModule , PreloadAllModules} from '@angular/router';
import {HomeComponent} from './modules/home/home.component'

const routes: Routes = [
{path: '' , loadChildren: () => import('./modules/home/home.module').then(m => m.HomeModule)}
];

@NgModule({
  imports: [RouterModule.forRoot(routes,{ preloadingStrategy: PreloadAllModules})],
})
export class AppRoutingModule { }
