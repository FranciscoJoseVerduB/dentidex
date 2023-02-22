

CREATE OR REPLACE FUNCTION procesarCita() RETURNS TRIGGER AS $historialClinicoDetalle$
    BEGIN  
        IF (TG_OP = 'DELETE') THEN 
				UPDATE Citas C
					SET Id_EstadoCita = 1 /* Cita pendiente */
				WHERE OLD.ID_Cita = C.ID;
            RETURN OLD; 
        ELSIF (TG_OP = 'INSERT') THEN 
				UPDATE Citas C
					SET Id_EstadoCita = 2 /* Cita confirmada */
				WHERE NEW.ID_Cita = C.ID;  
            RETURN NEW;
        END IF;
        RETURN NULL;  
    END;
$historialClinicoDetalle$ LANGUAGE plpgsql;

CREATE TRIGGER procesarCita
AFTER INSERT OR DELETE ON HistorialClinicoDetalle
    FOR EACH ROW EXECUTE PROCEDURE procesarCita();