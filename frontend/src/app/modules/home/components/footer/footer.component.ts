import { Component, OnInit } from '@angular/core';
import { VersionService } from '../../services/version.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

  version:string;

  constructor(private versionService:VersionService) { }

  ngOnInit(): void {
    this.versionService.getVersion().subscribe(v=>{this.version=v;});
  }

}
