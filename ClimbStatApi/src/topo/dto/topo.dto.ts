import { IsBoolean, IsNotEmpty, IsNumber, IsOptional, IsString } from "class-validator";

export class TopoDto {
    @IsNotEmpty()
    @IsBoolean()
    isFlash!: boolean;

    @IsNotEmpty()
    @IsNumber()
    nbAttempts!: number;

    @IsOptional()
    @IsString()
    comment?: string;
}
