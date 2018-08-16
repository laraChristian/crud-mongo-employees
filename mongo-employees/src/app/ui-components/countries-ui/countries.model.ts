import { CountryRequest } from "../../domain/countryRequest";
import { FormBuilder, FormGroup, Validators, FormControl } from "@angular/forms";
import { SelectItem } from "primeng/primeng";
import { Commons } from "../ui-utilities/commons";
import { Region } from "../../entities/regions";
import { Country } from "../../entities/country";
import { Mapper } from "../../mappers/mapper";

export class CountriesModel extends Commons implements Mapper {

    request: CountryRequest;
    countryForm: FormGroup;
    enableDelete: boolean;
    countryCols: any[];
    regionsItems: SelectItem[];
    regions: Array<Region>;
    countries: Array<Country>;

    constructor(private _formBuilder: FormBuilder) {
        super();
        this.initForm();
        this.clean();
        this.regionsItems = new Array<SelectItem>();
        this.regions = new Array<Region>();
        this.initDataTableColumns();
    }

    clean(): void {
        this.request = new CountryRequest();
        this.countryForm.reset();
        this.enableDelete = true;
    }

    initForm(): void {
        this.countryForm = this._formBuilder.group({
            'id': new FormControl('', Validators.nullValidator),
            'name': new FormControl('', Validators.required),
            'regionId': new FormControl('', Validators.required)
        })
    }


    mapFormControlsToRequest(request: any, form: FormGroup, fieldsToMap: Array<string>): void {
        console.log('start -- map-form-to-request method');
        fieldsToMap.forEach(field => {
            request[field] = form.controls[field].value;
        });
        console.log('end -- map-form-to-request method');
    }

    mapRequestToFormControls(request: any, form: FormGroup, fieldsToMap: Array<string>): void {
        console.log('start -- map-request-to-form method');
        fieldsToMap.forEach(field => {
            form.controls[field].setValue(request[field]);
        });
        console.log('end -- map-request-to-form method');
    }

    initDataTableColumns(): void {
        this.countries = new Array<Country>();
        this.countryCols = [{ field: 'name', header: 'Country' }, { field: 'regionName', header: 'Region' }]
    }

}