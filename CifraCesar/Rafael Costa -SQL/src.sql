select case when '&operacao' = 'Criptografar' then
          TRANSLATE( '&texto', 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz', 'DEFGHIJKLMNOPQRSTUVWXYZABCdefghijklmnopqrstuvwxyzabc' )
       when '&operacao' = 'Descriptografar' then
          TRANSLATE( '&texto', 'DEFGHIJKLMNOPQRSTUVWXYZABCdefghijklmnopqrstuvwxyzabc', 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz' )
       else
         'Preencha o campo operação com Criptografar ou Decriptografar'
       end as resultado
  from dual