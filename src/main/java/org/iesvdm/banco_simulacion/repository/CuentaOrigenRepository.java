package org.iesvdm.banco_simulacion.repository;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.banco_simulacion.model.CuentaOrigen;
import org.iesvdm.banco_simulacion.model.Transferencia_programada;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@Repository
public class CuentaOrigenRepository {
    private JdbcTemplate jdbcTemplate;

    public CuentaOrigenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<CuentaOrigen> getAll() {
        List<CuentaOrigen> listaCuentas = jdbcTemplate.query("""
                
                        Select * from cuenta_origen
   
                    """,
                (rs, rowNum)-> new CuentaOrigen(
                        rs.getLong("id"),
                        rs.getString("alias_cuenta"),
                        rs.getString("iban"))
        );

        log.info("Devueltos {} clientes",listaCuentas.size());
        log.debug("Devueltos {} clientes",listaCuentas.size());
        return listaCuentas;
    }

    public CuentaOrigen findById(Long id) {

        String sql = """
                Select * from cuenta_origen
                where id = ?
                """;
        CuentaOrigen cuentaOrigen = jdbcTemplate.queryForObject(sql, (ResultSet rs, int rowNum) -> CuentaOrigen.builder()
                        .id(rs.getLong("id"))
                        .alias_cuenta(rs.getString("alias_cuenta"))
                        .iban(rs.getString("iban"))
                        .build()
                , id);

        return cuentaOrigen;

    }

    public Transferencia_programada create(Transferencia_programada transferenciaProgramada) {

        String sql = """
                insert into transferencia_programada(cuenta_origen_id,nombre_beneficiario,iban_destino,importe,concepto,fecha_programada,fecha_creacion,estado)
                values(                                      ?,               ?,              ?,            ?,        ?,              ?,             ?,     ?)
                """;

        String[] ids = {"id"};

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {

            PreparedStatement ps = con.prepareStatement(sql, ids);
            ps.setLong(1, transferenciaProgramada.getCuenta_origen_id());
            ps.setString(2, transferenciaProgramada.getNombre_beneficiario());
            ps.setString(3, transferenciaProgramada.getIban_destino());
            ps.setBigDecimal(4, transferenciaProgramada.getImporte());
            ps.setString(5,transferenciaProgramada.getConcepto());
            ps.setTimestamp(6, Timestamp.valueOf(transferenciaProgramada.getFecha_programada()));
            ps.setTimestamp(7,Timestamp.valueOf(transferenciaProgramada.getFecha_creacion()));
            ps.setString(8, transferenciaProgramada.getEstado());

            return ps;
        }, keyHolder);

        transferenciaProgramada.setId(keyHolder.getKey().longValue());


        return transferenciaProgramada;
    }


}
