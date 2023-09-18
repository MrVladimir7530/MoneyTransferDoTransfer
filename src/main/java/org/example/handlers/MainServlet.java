package org.example.handlers;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.example.exceptions.ProcessException;
import org.example.model.*;
import org.example.service.ClientService;
import org.example.utils.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class MainServlet extends HttpServlet
{
    private static Logger log = LoggerFactory.getLogger(MainServlet.class.getSimpleName());
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String body = IOUtils.toString(req.getInputStream());
        if(StringUtils.isBlank(body))
        {
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(Common.getPrettyGson().toJson(new ClientTransferResponse("404", "body is blank")));
            return;
        }
        ClientTransferRequest r = Common.getPrettyGson().fromJson(body, ClientTransferRequest.class);
        ClientService clientService = null;
        try {
            clientService = new ClientService();
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            resp.setContentType("application/json");
            ProcessException processException = new ProcessException("code", "слишком много запросов", 417);
            resp.getWriter().println(Common.getPrettyGson().toJson(processException));
            return;
        }
        PaymentsResult result = new PaymentsResult();
        try {
            result = clientService.getResultTransfer(r.getFromPersonId(), r.getToPersonId(), r.getMoney());
        } catch (SQLException e) {
            result.setStatus(417);
        }
        if (result.getStatus()== 200) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else if (result.getStatus() == 417) {
            resp.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
        resp.setContentType("application/json");
        resp.getWriter().println(Common.getPrettyGson().toJson(result));
    }



}
