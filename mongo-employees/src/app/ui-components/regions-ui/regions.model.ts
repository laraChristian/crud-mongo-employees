import { RegionRequest } from "../../domain/regionRequest";
import { Region } from "../../entities/regions";
import { FormGroup, FormBuilder, FormControl, Validators } from "../../../../node_modules/@angular/forms";

export class RegionModel {

    regionForm: FormGroup;
    request: RegionRequest;
    regions: Array<Region>;
    enableDelete: boolean;
    cols: any[];
    loading: boolean;

    constructor(private _formGroup: FormBuilder) {
        this.initForm();
        this.clean();
        this.initColums();
        this.loading = false;
        this.regions = Array<Region>();
    }

    clean(): void {
        this.request = new RegionRequest();
        this.regionForm.reset();
        this.enableDelete = true;
    }

    initForm() {
        this.regionForm = this._formGroup.group({ 'id': new FormControl('', Validators.nullValidator), 'name': new FormControl('', Validators.required) });
    }

    initColums(): void {
        this.cols = [{ field: 'name', header: 'Region' }]
    }
}