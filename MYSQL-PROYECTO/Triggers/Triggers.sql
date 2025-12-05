USE BD_CONTROL_MESAS;

/*IDEA: El estado y fechas de la tabla PEDIDO siempre se ingresen por backend con java*/
DELIMITER $$
CREATE TRIGGER TRIGGER_FECHA_CIERRE
BEFORE UPDATE ON PEDIDO
FOR EACH ROW
BEGIN
	IF OLD.estado_pago = 'Pendiente' AND (NEW.estado_pago = 'Pagado' OR NEW.estado_pago = 'Cancelado') THEN
		SET NEW.fecha_hora_cierre = NOW();
	END IF;
END$$