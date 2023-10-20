package DAO;

import DTO.ClienteDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ClienteDAO {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;
    ArrayList<ClienteDTO> lista = new ArrayList<>();//Utilizado na pesquisa de usuário

    public ResultSet autenticacaoUsuario(ClienteDTO objclientedto) {
        conn = new ConexaoDAO().conectarBD();

        try {
            String sql = "select * from tbclientes where nome_cliente = ? and fone_cliente = ? and curso_cliente";

            pstm = conn.prepareStatement(sql);
            pstm.setString(1, objclientedto.getNome());
            pstm.setString(2, objclientedto.getCurso());
            pstm.setString(3, objclientedto.getFone());

            rs = pstm.executeQuery();
            return rs;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ClienteDAO: " + e);
            return null;
        }
    }

    //Cadastrar cliente
    public void cadastrarCliente(ClienteDTO objClienteDTO) {
        //Inserir dados no banco/ Lembrando escreva de acordo com seu banco de dados 
        String sql = "insert into tbclientes (nome_cliente, fone_cliente, curso_cliente) values(?, ?, ?)";

        //Novo objeto de conexão 
        conn = new ConexaoDAO().conectarBD();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, objClienteDTO.getNome());
            pstm.setString(2, objClienteDTO.getFone());
            pstm.setString(3, objClienteDTO.getCurso());

            pstm.execute();
            pstm.close();
            //Aviso para sucesso de cadastro 
        } catch (SQLException e) {
            JOptionPane.showInternalMessageDialog(null, "ClienteDAO" + e);
        }
    }

    //Listar clientes
    public ArrayList<ClienteDTO> PesquisarCliente() {

        //Código que busca as informações do banco 
        String sql = "Select * from tbclientes";

        conn = new ConexaoDAO().conectarBD();

        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            //Enquanto houver informações ele continua lendo, quando acabar ele para
            while (rs.next()) {

                ClienteDTO objClienteDTO = new ClienteDTO();
                objClienteDTO.setId_cliente(rs.getInt("id_cliente"));// Atributo tem que ser igual o do banco
                objClienteDTO.setNome(rs.getString("nome_cliente"));// Atributo tem que ser igual o do banco
                objClienteDTO.setFone(rs.getString("fone_cliente"));// Atributo tem que ser igual o do banco
                objClienteDTO.setCurso(rs.getString("curso_cliente"));// Atributo tem que ser igual o do banco
                objClienteDTO.setId_conta(rs.getInt("id_conta"));// Atributo tem que ser igual o do banco

                lista.add(objClienteDTO);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ClienteDAO" + e);
        }
        return lista;
    }
     //Alterar cliente

    public void AlterarUsuario(ClienteDTO objClienteDTO) {
        String sql = "update tbclientes set nome_cliente = ?, fone_cliente = ?, curso_cliente = ? where id_cliente = ?";

        conn = new ConexaoDAO().conectarBD();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, objClienteDTO.getNome());
            pstm.setString(2, objClienteDTO.getFone());
            pstm.setString(3, objClienteDTO.getCurso());
            pstm.setInt(4, objClienteDTO.getId_cliente());
            pstm.execute();
            pstm.close();
            JOptionPane.showMessageDialog(null, "Usuário e Senha Editados com sucesso!!!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "NovoClienteDAO Editar:" + e);
        }
    }

    //Excluir Usuário
    public void ExcluirUsuario(ClienteDTO objClienteDTO) {
        String sql = "delete from tbclientes where id_cliente = ?";
        conn = new ConexaoDAO().conectarBD();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, objClienteDTO.getId_cliente());
            pstm.execute();
            pstm.close();
            JOptionPane.showMessageDialog(null, "Cliente excluido com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "NovoClienteDAO Excluir:" + e);
        }
    }
}
